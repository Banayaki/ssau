# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.9

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /cygdrive/c/Users/Banayaki/.CLion2017.3/system/cygwin_cmake/bin/cmake.exe

# The command to remove a file.
RM = /cygdrive/c/Users/Banayaki/.CLion2017.3/system/cygwin_cmake/bin/cmake.exe -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /cygdrive/c/Users/Banayaki/CLionProjects/ssau/lp1

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /cygdrive/c/Users/Banayaki/CLionProjects/ssau/lp1/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/lp1.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/lp1.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/lp1.dir/flags.make

CMakeFiles/lp1.dir/main.cpp.o: CMakeFiles/lp1.dir/flags.make
CMakeFiles/lp1.dir/main.cpp.o: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/cygdrive/c/Users/Banayaki/CLionProjects/ssau/lp1/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/lp1.dir/main.cpp.o"
	/usr/bin/c++.exe  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/lp1.dir/main.cpp.o -c /cygdrive/c/Users/Banayaki/CLionProjects/ssau/lp1/main.cpp

CMakeFiles/lp1.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/lp1.dir/main.cpp.i"
	/usr/bin/c++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /cygdrive/c/Users/Banayaki/CLionProjects/ssau/lp1/main.cpp > CMakeFiles/lp1.dir/main.cpp.i

CMakeFiles/lp1.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/lp1.dir/main.cpp.s"
	/usr/bin/c++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /cygdrive/c/Users/Banayaki/CLionProjects/ssau/lp1/main.cpp -o CMakeFiles/lp1.dir/main.cpp.s

CMakeFiles/lp1.dir/main.cpp.o.requires:

.PHONY : CMakeFiles/lp1.dir/main.cpp.o.requires

CMakeFiles/lp1.dir/main.cpp.o.provides: CMakeFiles/lp1.dir/main.cpp.o.requires
	$(MAKE) -f CMakeFiles/lp1.dir/build.make CMakeFiles/lp1.dir/main.cpp.o.provides.build
.PHONY : CMakeFiles/lp1.dir/main.cpp.o.provides

CMakeFiles/lp1.dir/main.cpp.o.provides.build: CMakeFiles/lp1.dir/main.cpp.o


# Object files for target lp1
lp1_OBJECTS = \
"CMakeFiles/lp1.dir/main.cpp.o"

# External object files for target lp1
lp1_EXTERNAL_OBJECTS =

lp1.exe: CMakeFiles/lp1.dir/main.cpp.o
lp1.exe: CMakeFiles/lp1.dir/build.make
lp1.exe: CMakeFiles/lp1.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/cygdrive/c/Users/Banayaki/CLionProjects/ssau/lp1/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable lp1.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/lp1.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/lp1.dir/build: lp1.exe

.PHONY : CMakeFiles/lp1.dir/build

CMakeFiles/lp1.dir/requires: CMakeFiles/lp1.dir/main.cpp.o.requires

.PHONY : CMakeFiles/lp1.dir/requires

CMakeFiles/lp1.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/lp1.dir/cmake_clean.cmake
.PHONY : CMakeFiles/lp1.dir/clean

CMakeFiles/lp1.dir/depend:
	cd /cygdrive/c/Users/Banayaki/CLionProjects/ssau/lp1/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /cygdrive/c/Users/Banayaki/CLionProjects/ssau/lp1 /cygdrive/c/Users/Banayaki/CLionProjects/ssau/lp1 /cygdrive/c/Users/Banayaki/CLionProjects/ssau/lp1/cmake-build-debug /cygdrive/c/Users/Banayaki/CLionProjects/ssau/lp1/cmake-build-debug /cygdrive/c/Users/Banayaki/CLionProjects/ssau/lp1/cmake-build-debug/CMakeFiles/lp1.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/lp1.dir/depend

