// fit(canvas) - scales an HTML document to make the given canvas fit the window

function fit(canvas) {
	const factor = Math.min(
		window.innerWidth / canvas.width,
		window.innerHeight / canvas.height
	);
	document.body.style.transform = 'scale(' + factor + ')';
}
