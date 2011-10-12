@ECHO ON
FOR %%f IN (rst\*.rst) DO (
	FOR %%h IN (html\%%~nf.html) DO (
		C:\python27\python.exe "C:\Program Files (x86)\docutilsv2\tools\rst2html.py" %%f html\%%~nf.html --link-stylesheet --stylesheet-path=style/acceleo.css
	)
)
