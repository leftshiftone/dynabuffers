from subprocess import check_call
import sys
import re
import os

def test_ci():
    check_call(["poetry", "run", "pytest", '--junit-xml=$TEST_RESULTS_PATH/TEST-dynabuffers-python-junit.xml'])

def release():
    tag = sys.argv[1]
    print("Releasing version", tag)
    version = _version_from_tag(tag)
    check_call(["poetry", "run", "test_ci"])
    check_call(["poetry", "build"])
    check_call(["poetry", "version", version])
    check_call(["poetry", "publish", "-u", "$PYPI_USERNAME", "-p", "$PYPI_PASSWORD"], env=os.environ)

def _version_from_tag(tag):
    """
    Extracts the semver from the given tag (e.g: v0.1.0 -> 0.1.0)
    :param tag:
    :return:
    """
    pattern = re.compile('v\d+.\d+.\d+')
    if not pattern.match(tag):
        raise Exception(f"{tag} is not valid semver")
    return tag[1:]
