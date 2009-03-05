@ECHO OFF
FOR %%f IN (rst\*.rst) DO (
	FOR %%h IN (html\%%~nf.html) DO (
		IF %%~tf GTR %%~th (
			C:\dev\programs\python\python.exe C:\dev\programs\docutils\tools\rst2html.py %%f html\%%~nf.html --stylesheet=style/lsr.css
		)
	)
)
