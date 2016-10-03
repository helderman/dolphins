all: html5/underwater.png

html5/underwater.png: script-fu/create-underwater.scm
	script-fu/execute.sh "$<" '(create-underwater "$@")'
