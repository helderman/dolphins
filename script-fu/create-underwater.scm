; --- UNDER CONSTRUCTION ---

(define (create-underwater name)
	(let*
		(
			(image (car (gimp-image-new 512 512 RGB)))
			(back (car (gimp-layer-new image 512 512 RGB-IMAGE "Back" 100 NORMAL-MODE)))
			(left (car (gimp-layer-new image 640 512 RGB-IMAGE "Lava" 100 NORMAL-MODE)))
		)
		(gimp-image-insert-layer image back 0 0)
		(gimp-image-insert-layer image left 0 0)
		(gimp-drawable-fill back WHITE-FILL)
		(script-fu-lava image left 10.0 10.0 7.0 "Horizon 2" 0 0 0)
		(plug-in-gauss RUN-NONINTERACTIVE image left 20 20 1)
		(let*
			(
				(right (car (gimp-layer-copy left FALSE)))
				(mask (car (gimp-layer-create-mask right ADD-WHITE-MASK)))
			)
			(gimp-image-insert-layer image right 0 0)
			(gimp-context-set-foreground '(0 0 0))
			(gimp-context-set-background '(255 255 255))
			(gimp-layer-add-mask right mask)
			(gimp-edit-blend mask FG-BG-RGB-MODE NORMAL-MODE GRADIENT-LINEAR 100 0 REPEAT-NONE FALSE FALSE 1 0 FALSE 0 0 128 0)
		)
		(gimp-layer-set-offsets left -512 0)
		(gimp-layer-resize-to-image-size left)
		(gimp-layer-resize-to-image-size right)
		(file-png-save RUN-NONINTERACTIVE image (car (gimp-image-flatten image)) name name 0 9 0 0 0 1 1)
		;(file-png-save RUN-NONINTERACTIVE image left name name 0 9 0 0 0 1 1)
		(gimp-image-delete image)
	)
)
