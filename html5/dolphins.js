function dolphins() {
	window.removeEventListener('click', dolphins, false);
	window.removeEventListener('keypress', dolphins, false);

	document.body.className = 'running';
	document.getElementById('music').play();

	var deg2rad = Math.PI / 180;
	function sin(degrees) { return Math.sin(degrees * deg2rad); }
	function cos(degrees) { return Math.cos(degrees * deg2rad); }

	var canvas = document.getElementById('dolphins');
	var ctx = canvas.getContext('2d');

	var scaler = function() { fit(canvas); };
	scaler();
	window.onresize = scaler;

	var dolphin_x, dolphin_y, dolphin_z, dolphin_phase;
	var dolphin_dir = 0;
	var dolphin_amp = 20;

	var cam = {
		zoom: 1.4 * canvas.height,
		calculate_position: function(time) {
			if (time < 20000) {
				// Dolphins rushing in from behind.
				// (Actually, the dolphins are stationary;
				// it's the camera that is moving backward!)
				this.set_dir(20 - time / 2000);
				this.x = -0.12 * (time - 8000);
				this.y = -20;
				this.z = -20;
			}
			else {
				// Camera circling around the dolphins.
				this.set_dir(time / 100);
				this.x = -150 * this.cos;
				this.y = -150 * this.sin;
				this.z = 0;
			}
			return (this.zoom * this.dir) + 'px 0';
		},
		set_dir: function(degrees) {
			this.dir = degrees * deg2rad;
			this.cos = Math.cos(this.dir);
			this.sin = Math.sin(this.dir);
		},
		rotated: function(x, y) {
			return x * this.cos + y * this.sin;
		}
	};

	var pathmgr = {
		go: function(x, y, width) {
			if (width > 0) {
				ctx.beginPath();
				ctx.moveTo(this.x0, this.y0);
				ctx.lineTo(x, y);
				ctx.lineWidth = width + 0.5;
				ctx.stroke();
			}
			this.x0 = x;
			this.y0 = y;
		}
	};

	function flat_go_to(x, y, size, multiplier) {
		pathmgr.go(
			canvas.width / 2 + x * multiplier,
			canvas.height / 2 - y * multiplier,
			multiplier < 28 ? size * multiplier : 0);
	}
	function rotated_go_to(x, y, z, size) {
		flat_go_to(
			cam.rotated(-y, x),
			z,
			size,
			cam.zoom / cam.rotated(x, y));
	}
	function go_to(x, y, z, size, wave) {
		rotated_go_to(
			dolphin_x - cam.x + x * cos(dolphin_dir) + y * sin(dolphin_dir),
			dolphin_y - cam.y + y * cos(dolphin_dir) - x * sin(dolphin_dir),
			dolphin_z - cam.z + z + dolphin_amp * sin(dolphin_phase + x + wave),
			size);
	}
	function paint_tail_fin(wave) {
		for (var i = -61; i < -52; i++) {
			var width = 12 * (1 - sin(Math.abs(8 * (i + 55.25))));
			go_to(-63, width, 0, 0, wave);
			go_to(i, 0, 0, 2, wave);
			go_to(-63, -width, 0, 2, wave);
		}
	}
	function paint_dorsal_fin(wave) {
		var i = -13;
		go_to(i, 0, 0, 0, wave);
		while (++i < 0) {
			go_to(i, 0, 0, 0, wave);
			go_to(i - 15, 0, 12 * cos(4 * (i + 10)), 2, wave);
		}
	}
	function paint_flippers(wave) {
		for (var i = 9; i < 18; i++) {
			var z = -11 * cos(4 * (i - 9));
			go_to(i - 15, z, z, 0, wave);
			go_to(i, 0, 0, 2, wave);
			go_to(i - 15, -z, z, 2, wave);
		}
	}
	function paint_body(wave) {
		var i = -61;
		go_to(i, 0, 0, 0, wave);
		while (++i < 30) {
			go_to(i, 0, 0, 10 - 8 * cos(40 * Math.sqrt(30 - i) - 36), wave);
		}
	}
	function paint_dolphin(wave) {
		ctx.lineCap = "round";
		ctx.strokeStyle = "#135B8D";
		paint_tail_fin(wave);
		paint_dorsal_fin(wave);
		paint_flippers(wave);
		paint_body(wave);
	}
	function paint_dolphins(time) {
		for (var i = 20; i >= -20; i -= 20) {
			dolphin_x = i;
			dolphin_y = -2 * i;
			dolphin_z = i;
			dolphin_phase = 220 + i;
			paint_dolphin(0.12 * time);
		}
	}
	function paint_frame(time) {
		canvas.style.backgroundPosition = cam.calculate_position(time);
		ctx.clearRect(0, 0, canvas.width, canvas.height);
		paint_dolphins(time);
	}

	// Thanks to:
	// http://www.paulirish.com/2011/requestanimationframe-for-smart-animating/

	var time0;
	function animation_loop(time) {
		requestAnimationFrame(animation_loop);
		paint_frame(time - time0);
	}
	requestAnimationFrame(function(time) {
		animation_loop(time0 = time);
	});
}

var preloader = new Preloader(function() {
	document.body.className = 'loaded';
	window.addEventListener('click', dolphins, false);
	window.addEventListener('keypress', dolphins, false);
});
preloader.add_all_images();
preloader.done();
