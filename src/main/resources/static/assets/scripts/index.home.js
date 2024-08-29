const prevButton = document.getElementById('prevButton');
const nextButton = document.getElementById('nextButton');
const chartList = document.querySelector('.chart-content');
const chartItems = document.querySelectorAll('.list-item');

let currentSlide = 0;
const slideWidth = chartItems[0].offsetWidth;


nextButton.addEventListener('click', () => {
    if (currentSlide < chartItems.length - 1) {
        currentSlide++;
        chartList.style.transform = `translateX(-94rem)`;
        chartList.style.transition = 'transform 0.5s ease-in-out';
    }

    prevButton.addEventListener('click', () => {
        if (currentSlide >= 1) {
            currentSlide--;
            chartList.style.transform = `translateX(0)`;
            chartList.style.transition = 'transform 0.5s ease-in-out';
        }
    });
});