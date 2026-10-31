async function loadLeaderboard() {
    const container = document.getElementById('leaderboard');
    try {
        const response = await fetch('/quiz/leaderboard');
        const data = await response.json();

        if (!data || data.length === 0) {
            container.innerHTML = `
                <div style="text-align:center;padding:48px 0;color:#64748b;">
                    <div style="font-size:3rem;margin-bottom:12px;">&#127942;</div>
                    <p style="font-size:0.95rem;">No quiz results yet. Be the first!</p>
                </div>`;
            return;
        }

        const medals = ['&#129351;','&#129352;','&#129353;'];
        const rankBg = ['rgba(245,158,11,0.25)','rgba(148,163,184,0.2)','rgba(180,83,9,0.25)'];
        const rankColor = ['#fbbf24','#cbd5e1','#d97706'];
        const scoreColor = s => s >= 80 ? '#10b981' : s >= 60 ? '#f59e0b' : '#ef4444';

        container.innerHTML = data.map((item, i) => {
            const isTop = i < 3;
            const rankLabel = isTop ? medals[i] : `#${i + 1}`;
            const bg   = isTop ? rankBg[i]    : 'rgba(255,255,255,0.05)';
            const col  = isTop ? rankColor[i] : '#64748b';

            return `
            <div style="
                display:flex;
                align-items:center;
                gap:16px;
                padding:16px 20px;
                background:#1e2736;
                border:1px solid rgba(255,255,255,0.12);
                border-radius:14px;
                margin-bottom:10px;
                transition:all 0.18s;
            " onmouseover="this.style.borderColor='rgba(99,102,241,0.5)';this.style.transform='translateX(4px)'"
               onmouseout="this.style.borderColor='rgba(255,255,255,0.12)';this.style.transform='translateX(0)'">

                <div style="
                    width:40px;height:40px;
                    border-radius:10px;
                    background:${bg};
                    color:${col};
                    display:flex;align-items:center;justify-content:center;
                    font-weight:800;font-size:${isTop ? '1.3rem' : '0.85rem'};
                    flex-shrink:0;
                ">${rankLabel}</div>

                <div style="flex:1;">
                    <div style="font-size:0.95rem;font-weight:600;color:#f1f5f9;">User #${item.userId}</div>
                    <div style="font-size:0.78rem;color:#64748b;margin-top:3px;">&#128218; ${item.topic}</div>
                </div>

                <div style="font-size:1.4rem;font-weight:800;color:${scoreColor(item.score)};">${item.score}%</div>
            </div>`;
        }).join('');

    } catch (err) {
        container.innerHTML = `<div style="padding:12px 16px;background:rgba(239,68,68,0.1);border:1px solid rgba(239,68,68,0.3);border-radius:10px;color:#fca5a5;">&#9888; Failed to load leaderboard.</div>`;
    }
}

loadLeaderboard();
