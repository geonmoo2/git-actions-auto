document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.section2-toggle-btn').forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault();

            const sectionTop = this.closest('.section2-top');
            const sectionBottom = sectionTop.nextElementSibling;
            const sectionMid2 = sectionTop.closest('.section2-mid2');
            const arrowIcon = this.querySelector('.material-symbols-outlined');

            if (sectionBottom.style.display === "none" || sectionBottom.style.display === "") {
                sectionBottom.style.display = "block";
                const bottomHeight = sectionBottom.scrollHeight;
                sectionMid2.style.height = `${sectionMid2.scrollHeight + bottomHeight}px`;
                arrowIcon.classList.add('rotate');
            } else {
                const bottomHeight = sectionBottom.scrollHeight;
                sectionBottom.style.display = "none";
                sectionMid2.style.height = `${sectionMid2.scrollHeight - bottomHeight}px`;
                arrowIcon.classList.remove('rotate');
            }
        });
    });
});
