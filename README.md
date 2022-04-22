# JEP integration for SciJava

This project aims to check the ability to use [JEP](https://github.com/ninia/jep) backed by Maven and usable either the OS or Python
environment to use with [SciJava](https://scijava.org/).

## Requirements

- Installing jep via `pip`

```shell
pip install jep
```

- No need to set a `LD_LIBRARY_PATH` environment variable
- No need to set a `PYTHONHOME` environment variable

## How to use

### For Conda environment

```shell
conda activate scijava
mvn -Pexec
```

### For Venv environment

```shell
# For MacOS and Linux
source /path/to/venv/bin/activate
# For Windows
source C:\Users\username\path\to\venv\bin\activate.bat
mvn -Pexec
```

### For the global Python interpreter

```shell
mvn -Pexec
```

Then set the path to your Python executable folder commonly `/usr/bin` for **Linux** and `C:\Program Files\Python3x` or
`C:\Users\username\AppData\Local\Programs\Python3x` for **Windows**

After, set the path to your `site-packages` directory, where all pip packages are installed, and so JEP.

*You can find the path by running one of two commands*:

```shell
# Get info on a package
pip show <package_name>

# List all pip packages with the name, version and location
pip list -v
```

**_Note_**: Maven eats the Python-side output. To see that as well, you can do:

```
mvn package dependency:copy-dependencies
java -cp target/jep-integration-0.1.0-SNAPSHOT.jar:target/dependency/'*' net.imagej.jep.Main
```

## Available scenarios

### Basic JEP example

> - Simple JEP execution
> - Send and retrieve data from Python

### Examples with ImageJ

> - Get ImageJ version from Python
> - Open an image
> - More to come