// fit(canvas) - scales an HTML document to make the given canvas fit the window

function fit(canvas) {
	const factor = Math.min(
		window.innerWidth / canvas.width,
		window.innerHeight / canvas.height
	);
	const s = document.body.style;
	s.transform = 'scale(' + factor + ')';
	s.marginLeft = ((window.innerWidth - factor * canvas.width) / 2) + 'px';
}
