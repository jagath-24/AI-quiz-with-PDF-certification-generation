document.getElementById('verifyForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    const certNumber = document.getElementById('certificateNumber').value.trim();
    const resultDiv = document.getElementById('verificationResult');
    const resultContent = document.getElementById('resultContent');

    try {
        const response = await fetch('/certificate/verify', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `certificateNumber=${encodeURIComponent(certNumber)}`
        });

        resultDiv.style.display = 'block';

        if (response.ok) {
            const cert = await response.json();
            resultContent.innerHTML = `
                <div class="alert alert-success">✅ Certificate is valid!</div>
                <div class="detail-row"><span class="detail-label">Certificate #</span><span class="detail-value">${cert.certificateNumber}</span></div>
                <div class="detail-row"><span class="detail-label">Score</span><span class="detail-value">${cert.score}%</span></div>
                <div class="detail-row"><span class="detail-label">Issued</span><span class="detail-value">${new Date(cert.issueDate).toLocaleDateString()}</span></div>
            `;
        } else {
            resultContent.innerHTML = `<div class="alert alert-error">❌ Certificate not found or invalid.</div>`;
        }
    } catch (err) {
        resultDiv.style.display = 'block';
        resultContent.innerHTML = `<div class="alert alert-error">⚠️ Error: ${err.message}</div>`;
    }
});
