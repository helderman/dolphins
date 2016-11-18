all: html5/underwater.jpg

html5/underwater.jpg: script-fu/create-underwater.scm
	script-fu/execute.sh "$<" '(create-underwater "$@")'
