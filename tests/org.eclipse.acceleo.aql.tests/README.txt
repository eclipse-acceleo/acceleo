= End-of-line characters =

Some tests may fail due to inconsistencies between end-of-line characters used in the tests, in the code and to checkout the source repository.
Make sure autocrlf is turned *OFF* when checking out the repository.
This option can also be set locally using the following commands (at the root of the repository, with no uncommitted local changes):
git config core.autocrlf false
git rm --cached -r .
git reset --hard
