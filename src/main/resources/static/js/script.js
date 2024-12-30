/*********************************************   기존 SIDE BAR JS   *********************************************/
function toggleMenu(show) {
    const sideMenu = document.getElementById('sideMenu');
    if (show) {
        sideMenu.style.visibility = 'visible';
        sideMenu.style.opacity = '1';
        sideMenu.classList.add('active');
    } else {
        sideMenu.classList.remove('active');
        sideMenu.style.visibility = 'hidden';
        sideMenu.style.opacity = '0';
    }
}

/*********************************************     BALANCE GAME     *********************************************/
const questions = document.querySelectorAll('.question');
const progressBarFill = document.getElementById('progress-bar-fill');
const form = document.getElementById('balance-game-form');
let currentQuestionIndex = 0;

function updateProgress() {
    const progress = ((currentQuestionIndex + 1) / questions.length) * 100;
    progressBarFill.style.width = progress + '%';
}

function showQuestion(index) {
    questions.forEach((question, i) => {
        question.classList.toggle('hidden', i !== index);
    });
    updateProgress();
}

form.addEventListener('change', (event) => {
    if (event.target.classList.contains('answer-option')) {
        if (currentQuestionIndex < questions.length - 1) {
            currentQuestionIndex++;
            showQuestion(currentQuestionIndex);
        } else {
            form.submit();
        }
    }
});


showQuestion(currentQuestionIndex);