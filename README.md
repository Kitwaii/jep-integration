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

```shell
mvn -Pexec
```

**_Note_**: Maven eats the Python-side output. To see that as well, you can do:

```
mvn package dependency:copy-dependencies
java -cp target/jep-integration-0.1.0-SNAPSHOT.jar:target/dependency/'*' net.imagej.jep.Main
```

Then set the path to your Python executable folder :

- For Python interpreter,
  - Commonly `/usr/bin` for **Linux**
  - `C:\Program Files\Python3<x>` or `C:\Users\<username>\AppData\Local\Programs\Python3<x>` for **Windows**
- For Conda and consort environment,
  - `/home/<username>/.conda/envs/<env_name>/bin` for **Linux**
  - `C:\Users\<username>\.conda\envs\<env_name>\bin`
  - **YOUR CONDA ENVIRONMENT MUST BE ALREADY ACTIVATED**
- For Venv environment : The full path to the `bin` directory

## Available scenarios

### Basic JEP example

> - Simple JEP execution
> - Send and retrieve data from Python
> - Calling Java class in Python as a usual Python import

### Examples with ScyJava

> - Use `jimport` function

### Examples with ImageJ

> - Get ImageJ version from Python
> - Open an image
> - More to come

### Misc

> - Example of a custom function in Python to import classes/objects from a package path
    >
- This case is created to know how JEP could be integrated in the jimport function of SciJava