package com.example.sl.domain.service;

import com.example.sl.domain.dto.BookDto;
import com.example.sl.domain.dto.PaymentDto;
import com.example.sl.domain.service.BookService;
import com.example.sl.domain.service.PaymentService;
import com.example.sl.entity.*;
import com.example.sl.repository.*;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameInfoRepository gameInfoRepository;

    @Override
    public List<GameInfoEntity> getAllGameInfo() {
        return gameInfoRepository.findAll();
    }

    @Override
    public GameInfoEntity getGameInfoById(Long gameInfoId) {
        return gameInfoRepository.findById(gameInfoId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid gameInfoId: " + gameInfoId));
    }

    @Override
    public BookEntity makeBook(BookDto bookDto) {
        if (bookDto.getSeatid() == null) {
            throw new IllegalArgumentException("seatid must not be null");
        }

        Optional<SeatEntity> seatEntityOptional = seatRepository.findById(bookDto.getSeatid());
        if (!seatEntityOptional.isPresent()) {
            throw new IllegalArgumentException("Invalid seatId");
        }
        SeatEntity seatEntity = seatEntityOptional.get();

        User user = userRepository.findByUserName(bookDto.getName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username: " + bookDto.getName()));

        GameInfoEntity gameInfo = gameInfoRepository.findById(Long.parseLong(bookDto.getGameinfoId()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid gameinfoId: " + bookDto.getGameinfoId()));

        BookEntity bookEntity = new BookEntity();
        bookEntity.setSeatid(bookDto.getSeatid());
        bookEntity.setSeat(bookDto.getSeat());
        bookEntity.setName(bookDto.getName());
        bookEntity.setGameinfo(bookDto.getGameinfoId());
        bookEntity.setGameName(gameInfo.getGameName());  // 게임 이름 설정
        bookEntity.setMainZone(bookDto.getMainZone());
        bookEntity.setZone(bookDto.getZone());
        bookEntity.setDate(LocalDateTime.now());
        bookEntity.setBookstatus("결제 취소");
        bookEntity.setPayid(bookDto.getPayid());
        bookEntity.setTotalPrice(bookDto.getTotalPrice());
        bookEntity.setMainZone(bookDto.getMainZone());
        bookEntity.setZone(bookDto.getZone());
        bookEntity.setImpUid(bookDto.getImpUid());
        bookEntity.setUser(user);

        return bookRepository.save(bookEntity);
    }



    @Override
    public void confirmBook(Long bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bookId"));

        bookEntity.setBookstatus("예매 완료");

        // 여러 좌석 처리
        String[] seatNumbers = bookEntity.getSeat().split(", ");
        for (String seatNumber : seatNumbers) {
            List<SeatEntity> seatEntities = seatRepository.findBySeatNumber(seatNumber);
            for (SeatEntity seatEntity : seatEntities) {
                seatEntity.setReserved(true);
                seatRepository.save(seatEntity);
            }
        }

        bookRepository.save(bookEntity);
    }

    @Override
    public BookEntity cancelBook(String bookId) throws IOException, IamportResponseException {
        Optional<BookEntity> bookEntityOptional = bookRepository.findById(Long.parseLong(bookId));
        if (!bookEntityOptional.isPresent()) {
            throw new IllegalArgumentException("Invalid bookId");
        }

        BookEntity bookEntity = bookEntityOptional.get();

        // 예매 상태 확인
        if ("예매 취소 완료".equalsIgnoreCase(bookEntity.getBookstatus())) {
            throw new IllegalArgumentException("이미 취소된 예매입니다");
        }

        bookEntity.setBookstatus("예매 취소 완료");

        String[] seatNumbers = bookEntity.getSeat().split(", ");
        for (String seatNumber : seatNumbers) {
            List<SeatEntity> seatEntities = seatRepository.findBySeatNumber(seatNumber);
            if (!seatEntities.isEmpty()) {
                for (SeatEntity seatEntity : seatEntities) {
                    seatEntity.setReserved(false);
                    seatRepository.save(seatEntity);
                }
            }
        }

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setCancelRequestAmount(bookEntity.getTotalPrice());
        paymentService.cancelPayment(bookEntity.getImpUid(), paymentDto);

        return bookRepository.save(bookEntity);
    }


    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<String> getZones() {
        return seatRepository.findAll()
                .stream()
                .map(SeatEntity::getZone)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatEntity> getAvailableSeatsByZone(String zone) {
        return seatRepository.findByZoneAndReservedFalse(zone);
    }

    @Override
    public List<SeatEntity> getAvailableSeatsByMainZoneAndZone(String mainZone, String zone) {
        return seatRepository.findByMainZoneAndZone(mainZone, zone);
    }

    @Override
    public List<String> getZonesByMainZone(String mainZone) {
        return seatRepository.findDistinctZonesByMainZone(mainZone);
    }

    @Override
    public PaymentEntity savePayment(PaymentDto paymentDto) {
        BookEntity bookEntity = bookRepository.findById(paymentDto.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid bookId"));

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setAmount(paymentDto.getAmount());
        paymentEntity.setPaymentDateTime(paymentDto.getPaymentDateTime());
        paymentEntity.setPaymentMethod(paymentDto.getPaymentMethod());
        paymentEntity.setPaymentStatus(paymentDto.getPaymentStatus());
        paymentEntity.setImpUid(paymentDto.getImpUid());
        paymentEntity.setMerchantUid(paymentDto.getMerchantUid());
        paymentEntity.setBookEntity(bookEntity);

        return paymentRepository.save(paymentEntity);
    }

    @Override
    public BookEntity updateImpUid(Long bookId, String impUid) {
        Optional<BookEntity> bookEntityOptional = bookRepository.findById(bookId);
        if (!bookEntityOptional.isPresent()) {
            throw new IllegalArgumentException("Invalid bookId");
        }

        BookEntity bookEntity = bookEntityOptional.get();
        bookEntity.setImpUid(impUid);

        return bookRepository.save(bookEntity);
    }

    @Override
    public void checkPendingReservations() {
        List<BookEntity> pendingBooks = bookRepository.findByBookstatus("결제 취소");
        for (BookEntity book : pendingBooks) {
            book.setBookstatus("예매 취소 완료");
            Optional<SeatEntity> seatEntityOptional = seatRepository.findById(book.getSeatid());
            if (seatEntityOptional.isPresent()) {
                SeatEntity seatEntity = seatEntityOptional.get();
                seatEntity.setReserved(false);
                seatRepository.save(seatEntity);
            }
            bookRepository.save(book);
        }
    }

    @Override
    public void cancelPendingReservation(Long bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bookId"));

        bookEntity.setBookstatus("예매 취소 완료");
        Optional<SeatEntity> seatEntityOptional = seatRepository.findById(bookEntity.getSeatid());
        if (seatEntityOptional.isPresent()) {
            SeatEntity seatEntity = seatEntityOptional.get();
            seatEntity.setReserved(false);
            seatRepository.save(seatEntity);
        }
        bookRepository.save(bookEntity);
    }

    @Override
    public BookEntity findById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bookId: " + bookId));
    }


    @Override
    public Page<BookEntity> getBookingsByUser(String username, Pageable pageable) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username: " + username));
        return bookRepository.findByUserOrderByDateDesc(user, pageable);
    }

}
