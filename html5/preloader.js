function preloader(callback) {
	var loading = 0;
	var images = document.getElementsByTagName('img');
	var handler = function(mask) {
		return function() {
			if ((loading &= ~mask) == 0) callback();
		};
	};
	for (var i = 0; i < images.length; i++) {
		var image = images[i];
		if (image.className == 'preload') {
			var mask = 1 << i;
			loading |= mask;
			image.onload = handler(mask);
			image.src = image.alt;
		}
	}
}
