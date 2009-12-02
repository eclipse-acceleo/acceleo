@ECHO OFF
FOR %%f IN (rst\*.rst) DO (
	FOR %%h IN (html\%%~nf.html) DO (
		IF %%~tf GTR %%~th (
			C:\python26\python.exe "C:\Program Files\docutils\tools\rst2html.py" %%f html\%%~nf.html --link-stylesheet --stylesheet-path=style/acceleo.css
		)
	)
)
