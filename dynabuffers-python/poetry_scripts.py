import os
import shutil
from subprocess import check_call

POETRY_PROJECT_FILE = "pyproject.toml"


def clean():
    shutil.rmtree("dist", ignore_errors=True)
    shutil.rmtree("build", ignore_errors=True)
    shutil.rmtree("dynabuffers.egg-info", ignore_errors=True)
    shutil.rmtree(".pytest_cache", ignore_errors=True)


def install():
    _execute(["poetry", "install"])


def test():
    _execute(["poetry", "run", "pytest", "--junit-xml=build/test/TEST-junit.xml", "--cov=dynabuffers",
              "--cov-report=xml:build/coverage/coverage.xml", "--cov-report=term", "-s"])


def build():
    _execute(["poetry", "build"])


def publish():
    _execute(["poetry", "publish"])


def _execute(command, exec_dir=None):
    cmd = command.copy()
    print(f"Executing: {' '.join(cmd)}")
    for e in cmd:
        if e.startswith("$"):
            cmd[cmd.index(e)] = os.getenv(cmd[cmd.index(e)].replace("$", ""))
    check_call(cmd, cwd=exec_dir, env=os.environ)
