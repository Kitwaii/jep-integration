# JEP integration for SciJava

This project aims to check the ability to use [JEP](https://github.com/ninia/jep) backed by Maven and usable either the OS or Python
environment to use with [SciJava](https://scijava.org/).

## Requirements

- Installing jep via `pip`

```shell
pip install jep
```

- For **Linux** users : export the variable *LD_PRELOAD* with the path of the
  *libpython.so* file in your terminal
  - It will let JEP to enable the Numpy support
  - Normally on the release of JEP 4.1 (~Autumn 2022), this step will be useless

```shell
export LD_PRELOAD=/path/to/python/libpython3.x.so
# i.e. with a Python interpreter but it can be also from Conda or Venv
export LD_PRELOAD=/usr/bin/libpython3.10.so.1.0
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
  - `C:\Users\<username>\.conda\envs\<env_name>\bin` for **Windows**
  - *It is not necessary to activate the environment*
- For Venv environment : The full path to the `bin` directory
  - *It is not necessary to activate the environment*

## Available scenarios

### Basic JEP example

> - Simple JEP execution
> - Send and retrieve data from Python
> - Calling Java class in Python as a usual Python import
> - Check if JEP can import Numpy

### Examples with ImageJ

> - Get ImageJ version from Python
> - Open an image
> - More to come

### Examples with ScyJava

> - Use `jimport` function
    >
- Available only if you have a development version of ScyJava containing the JEP implementation

### Misc

> - Example of a custom function in Python to import classes/objects from a package path
    >
- This case is created to know how JEP could be integrated in the jimport function of SciJava