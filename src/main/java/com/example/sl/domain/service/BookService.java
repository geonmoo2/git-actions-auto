package com.example.sl.domain.service;

import com.example.sl.domain.dto.BookDto;
import com.example.sl.domain.dto.PaymentDto;
import com.example.sl.entity.BookEntity;
import com.example.sl.entity.GameInfoEntity;
import com.example.sl.entity.PaymentEntity;
import com.example.sl.entity.SeatEntity;
import com.siot.IamportRestClient.exception.IamportResponseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface BookService {
    BookEntity makeBook(BookDto bookDto);
    void confirmBook(Long bookId);
    BookEntity cancelBook(String bookId) throws IOException, IamportResponseException;
    void deleteById(Long id);
    List<BookEntity> getAllBooks();
    List<String> getZones();
    List<String> getZonesByMainZone(String mainZone);
    List<SeatEntity> getAvailableSeatsByZone(String zone);
    List<SeatEntity> getAvailableSeatsByMainZoneAndZone(String mainZone, String zone);
    PaymentEntity savePayment(PaymentDto paymentDto);
    BookEntity updateImpUid(Long bookId, String impUid);
    void checkPendingReservations();
    void cancelPendingReservation(Long bookId);
    BookEntity findById(Long bookId);

    List<GameInfoEntity> getAllGameInfo();
    GameInfoEntity getGameInfoById(Long gameInfoId);
    Page<BookEntity> getBookingsByUser(String username, Pageable pageable);
}
