function showNotice(msg, type) {
    let el = document.getElementById('genNotice');
    if (!el) {
        el = document.createElement('div');
        el.id = 'genNotice';
        document.querySelector('.generate-box').after(el);
    }
    el.className = `alert alert-${type}`;
    el.textContent = msg;
    el.style.display = 'flex';
}

document.getElementById('generateBtn').addEventListener('click', async function () {
    const topic      = document.getElementById('topic').value.trim();
    const difficulty = document.getElementById('difficulty').value;
    const count      = document.getElementById('questionCount').value;

    if (!topic) {
        showNotice('⚠️ Please enter a topic first.', 'error');
        return;
    }

    this.disabled    = true;
    this.textContent = 'Generating…';

    try {
        const response = await fetch(
            `/api/questions/generate?topic=${encodeURIComponent(topic)}&difficulty=${encodeURIComponent(difficulty)}&count=${count}`,
            { method: 'POST' }
        );

        const payload = await response.json().catch(() => ({}));
        if (response.ok) {
            const questions = Array.isArray(payload) ? payload : [];
            if (!questions.length) throw new Error('The AI did not return any questions.');
            document.getElementById('questionIds').value = questions.map(q => q.id).join(',');
            this.textContent = 'Questions Ready!';
            this.classList.remove('btn-primary');
            this.classList.add('btn-success');
            showNotice(`${questions.length} AI questions generated. Click Start Quiz to begin.`, 'success');
        } else {
            showNotice(`${payload.error || 'AI generation failed.'} You can still start with the built-in question bank for supported topics.`, 'error');
            this.disabled  = false;
            this.textContent = 'Generate AI Questions';
        }
    } catch (error) {
        showNotice(`${error.message} You can still start with the built-in question bank for supported topics.`, 'error');
        this.disabled  = false;
        this.textContent = 'Generate AI Questions';
    }
});
