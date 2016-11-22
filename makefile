all: html5/dolphins.png html5/underwater.jpg

html5/dolphins.png: script-fu/create-title.scm
	script-fu/execute.sh "$<" '(create-title "$@" "Dolphins" "Sans Bold")'

html5/underwater.jpg: script-fu/create-underwater.scm
	script-fu/execute.sh "$<" '(create-underwater "$@")'
