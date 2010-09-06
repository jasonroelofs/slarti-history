#/*
#-----------------------------------------------------------------------------
#Filename:    CMakeLists.txt
#-----------------------------------------------------------------------------
#
#This source file is part of the
#   ___                 __    __ _ _    _ 
#  /___\__ _ _ __ ___  / / /\ \ (_) | _(_)
# //  // _` | '__/ _ \ \ \/  \/ / | |/ / |
#/ \_// (_| | | |  __/  \  /\  /| |   <| |
#\___/ \__, |_|  \___|   \/  \/ |_|_|\_\_|
#      |___/                              
#      Tutorial Framework
#      http://www.ogre3d.org/tikiwiki/
#-----------------------------------------------------------------------------
#*/
cmake_minimum_required(VERSION 2.8)

set(PROJECT_NAME slartibartfast)
project(${PROJECT_NAME})

get_filename_component(PROJECT_ROOT_DIR "." ABSOLUTE)

if(WIN32)
  set(CMAKE_MODULE_PATH "$ENV{OGRE_HOME}/CMake/;${CMAKE_MODULE_PATH}")
  set(OGRE_SAMPLES_INCLUDEPATH
    $ENV{OGRE_HOME}/Samples/include
    )
endif(WIN32)

if(UNIX)
  set(CMAKE_MODULE_PATH "/usr/local/lib/OGRE/cmake/;${CMAKE_MODULE_PATH}")
  set(OGRE_SAMPLES_INCLUDEPATH
    /usr/local/share/OGRE/samples/Common/include/
    )
endif(UNIX)

if(APPLE)
  set(OGRE_HOME $ENV{OGRE_HOME})
  set(CMAKE_MODULE_PATH "${OGRE_HOME}/CMake;${CMAKE_MODULE_PATH}")
  set(CMAKE_OSX_ARCHITECTURES "i386")

  include_directories("${OGRE_HOME}/include/OGRE/OSX")

  FIND_LIBRARY(COCOA_LIBRARY Cocoa)
  FIND_LIBRARY(CARBON_LIBRARY Carbon)
  FIND_LIBRARY(IOKit_LIBRARY IOKit)
  MARK_AS_ADVANCED(COCOA_LIBRARY)
  MARK_AS_ADVANCED(CARBON_LIBRARY)
  MARK_AS_ADVANCED(IOKit_LIBRARY)

  set(OGRE_LIB_TYPE SHARED)
  set(OGRE_STATIC OFF)

  set(EXTRA_LIBS ${COCOA_LIBRARY} ${CARBON_LIBRARY} ${IOKit_LIBRARY})
endif(APPLE)

if (CMAKE_BUILD_TYPE STREQUAL "")
  # CMake defaults to leaving CMAKE_BUILD_TYPE empty. This screws up
  # differentiation between debug and release builds.
  set(CMAKE_BUILD_TYPE "RelWithDebInfo" CACHE STRING "Choose the type of build, options are: None (CMAKE_CXX_FLAGS or CMAKE_C_FLAGS used) Debug Release RelWithDebInfo MinSizeRel." FORCE)
endif ()

set(CMAKE_INSTALL_PREFIX "${CMAKE_CURRENT_BINARY_DIR}/dist")

set(OGRE_FOUND "TRUE")
find_package(OGRE REQUIRED)

if(NOT "${OGRE_VERSION_NAME}" STREQUAL "Cthugha")
  message(SEND_ERROR "You need Ogre 1.7 Cthugha to build this.")
endif()

find_package(OIS REQUIRED)

if(NOT OIS_FOUND)
  message(SEND_ERROR "Failed to find OIS.")
endif()

# Find Boost
if (NOT OGRE_BUILD_PLATFORM_IPHONE)
  if (WIN32 OR APPLE)
    set(Boost_USE_STATIC_LIBS TRUE)
  else ()
    # Statically linking boost to a dynamic Ogre build doesn't work on Linux 64bit
    set(Boost_USE_STATIC_LIBS ${OGRE_STATIC})
  endif ()
  if (MINGW)
    # this is probably a bug in CMake: the boost find module tries to look for
    # boost libraries with name libboost_*, but CMake already prefixes library
    # search names with "lib". This is the workaround.
    set(CMAKE_FIND_LIBRARY_PREFIXES ${CMAKE_FIND_LIBRARY_PREFIXES} "")
  endif ()
  set(Boost_ADDITIONAL_VERSIONS "1.42" "1.42.0" "1.41.0" "1.41" "1.40.0" "1.40" "1.39.0" "1.39" "1.38.0" "1.38" "1.37.0" "1.37" )
  # Components that need linking (NB does not include header-only components like bind)
  set(OGRE_BOOST_COMPONENTS thread date_time)
  find_package(Boost COMPONENTS ${OGRE_BOOST_COMPONENTS} QUIET)
  if (NOT Boost_FOUND)
    # Try again with the other type of libs
    set(Boost_USE_STATIC_LIBS NOT ${Boost_USE_STATIC_LIBS})
    find_package(Boost COMPONENTS ${OGRE_BOOST_COMPONENTS} QUIET)
  endif()
  find_package(Boost QUIET)

  # Set up referencing of Boost
  include_directories(${Boost_INCLUDE_DIR})
  add_definitions(-DBOOST_ALL_NO_LIB)
  set(OGRE_LIBRARIES ${OGRE_LIBRARIES} ${Boost_LIBRARIES})
endif()

## 
# QtOgre
##
set(QtOGRE_LIB_PATH "./vendor/QtOgre/build/Debug")
set(QtOGRE_INCLUDE_PATH "./vendor/QtOgre/include")

FIND_PACKAGE(Qt4 REQUIRED)
SET(QT_USE_QTGUI 1)
SET(QT_USE_QTSCRIPT 1)
SET(QT_USE_QTXML 1)
INCLUDE(${QT_USE_FILE})

##
# Source and Header Files
##
FILE(GLOB_RECURSE SRCS "./src/*.cpp")
FILE(GLOB_RECURSE HDRS "./include/*.h")

include_directories( 
  "${PROJECT_ROOT_DIR}/include"
  ${OIS_INCLUDE_DIRS}
  ${OGRE_INCLUDE_DIRS}
  "${OGRE_INCLUDE_DIRS}/OGRE"
  ${QtOGRE_INCLUDE_PATH}
  )

add_executable(${PROJECT_NAME} ${SRCS})

# Add the path where the Ogre framework was found
set(OGRE_FRAMEWORK_PATH "${OGRE_HOME}/lib/release")
set(OIS_FRAMEWORK_PATH "${OGRE_HOME}/lib/release")
set_target_properties(${PROJECT_NAME} PROPERTIES
  LINK_FLAGS "-F${OGRE_FRAMEWORK_PATH} -framework Ogre -L${QtOGRE_LIB_PATH} -lQtOgre"
)

target_link_libraries(${PROJECT_NAME} ${OGRE_LIBRARIES} ${OIS_LIBRARIES} ${QT_LIBRARIES} ${EXTRA_LIBS})

file(MAKE_DIRECTORY ${CMAKE_CURRENT_BINARY_DIR}/dist/bin)
file(MAKE_DIRECTORY ${CMAKE_CURRENT_BINARY_DIR}/dist/media)
