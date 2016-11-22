(define (create-title name text font)
	(let*
		(
			(scale 4)
			(w (* scale 480))
			(h (* scale 360))

			(image (car (gimp-image-new w h RGB)))
			(bumap (car (gimp-layer-new image w h RGB-IMAGE "Bump map" 100 NORMAL-MODE)))
			(final (car (gimp-layer-new image w h RGBA-IMAGE "Final" 100 NORMAL-MODE)))
		)
		(gimp-image-insert-layer image bumap 0 0)
		(gimp-image-insert-layer image final 0 0)

		; bump map is black; final layer is transparent
		(gimp-context-set-foreground '(255 255 255))
		(gimp-context-set-background '(0 0 0))
		(gimp-drawable-fill bumap BACKGROUND-FILL)
		(gimp-drawable-fill final TRANSPARENT-FILL)

		; put white text on bump map
		(gimp-floating-sel-anchor (car (let*
			(
				(size (* scale 72))
				(extends (gimp-text-get-extents-fontname text size PIXELS font))
				(x (/ (- w (car extends)) 2))
				(y (/ (- h (cadr extends)) 2))
			)
			(gimp-text-fontname image bumap x y text -1 TRUE size PIXELS font)
		)))

		; round the corners
		(let*
			(
				(blur (* scale 10))
			)
			(plug-in-gauss RUN-NONINTERACTIVE image bumap blur blur 1)
		)
		(gimp-levels bumap HISTOGRAM-VALUE 117 138 1.0 0 255)

		; blur the edges
		(let*
			(
				(blur (* scale 3.5))
			)
			(plug-in-gauss RUN-NONINTERACTIVE image bumap blur blur 1)
		)

		; make selection
		(gimp-context-set-antialias TRUE)
		(gimp-context-set-sample-merged FALSE)
		(gimp-context-set-sample-threshold-int 15)
		(gimp-image-select-color image CHANNEL-OP-REPLACE bumap '(0 0 0))
		(gimp-selection-invert image)

		; gradient
		(let *
			(
				(black-y (* scale 84))
				(white-y (* scale 244))
			)
			(gimp-edit-blend final FG-BG-RGB-MODE NORMAL-MODE GRADIENT-LINEAR 100 0 REPEAT-NONE FALSE FALSE 1 0 TRUE 0 white-y 0 black-y)
		)
		(gimp-selection-none image)

		; apply bump map
		(plug-in-bump-map RUN-NONINTERACTIVE image final bumap 135 45 6 0 0 0 0 TRUE FALSE LINEAR)
		(gimp-image-remove-layer image bumap)

		; map colors
		(gimp-context-set-gradient "Horizon 2")
		(plug-in-gradmap RUN-NONINTERACTIVE image final)

		; shadow
		(let*
			(
				(offset (* scale 4))
				(blur (* scale 7.5))
			)
			(script-fu-drop-shadow image final offset offset blur '(0 0 0) 60 TRUE)
		)

		; save
		(gimp-image-scale image 480 360)
		(file-png-save RUN-NONINTERACTIVE image (car (gimp-image-merge-visible-layers image CLIP-TO-IMAGE)) name name 0 9 0 0 0 1 1)
		(gimp-image-delete image)
	)
)
