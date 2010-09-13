
cmake_minimum_required(VERSION 2.6)

set(PROJECT_NAME slartibartfast)
project(${PROJECT_NAME})

if(APPLE)
  set(XCODE_ATTRIBUTE_SDKROOT macosx10.4)
  set(CMAKE_OSX_ARCHITECTURES "i386")
endif(APPLE)


####################
## Vendor Libraries
####################

# OIS
set(OIS_HOME ${CMAKE_CURRENT_SOURCE_DIR}/vendor/OIS)
set(OIS_INCLUDE_DIR ${CMAKE_CURRENT_SOURCE_DIR}/vendor/OIS/include)

subdirs(vendor/OIS)

# FreeImage
set(FREEIMAGE_INCLUDE ${CMAKE_CURRENT_SOURCE_DIR}/vendor/FreeImage)

subdirs(vendor/FreeImage)

# OGRE
set(OGRE_STATIC TRUE)
set(OGRE_THREAD_PROVIDER  0)
set(OGRE_BUILD_SAMPLES FALSE)
set(OGRE_BUILD_TOOLS FALSE)

set(OGRE_BUILD_PLUGIN_BSP FALSE)
set(OGRE_BUILD_PLUGIN_OCTREE FALSE)
set(OGRE_BUILD_PLUGIN_PCZ FALSE)
set(OGRE_BUILD_PLUGIN_PFX FALSE)
set(OGRE_BUILD_COMPONENT_PAGING FALSE)
set(OGRE_BUILD_COMPONENT_TERRAIN FALSE)

set(OGRE_HOME ${CMAKE_CURRENT_SOURCE_DIR}/vendor/Ogre)
set(OGRE_INCLUDE_DIR ${OGRE_HOME}/OgreMain/include ${CMAKE_CURRENT_SOURCE_DIR}/build/vendor/Ogre/include)

subdirs(vendor/Ogre)

##
# Building the project itself
##

find_package(Boost QUIET)

# Set up referencing of Boost
include_directories(${Boost_INCLUDE_DIR})
add_definitions(-DBOOST_ALL_NO_LIB)
set(OGRE_LIBRARIES ${OGRE_LIBRARIES} ${Boost_LIBRARIES})

FILE(GLOB_RECURSE SRCS "./src/*.cpp")
FILE(GLOB_RECURSE HDRS "./include/*.h")

include_directories( 
  ${CMAKE_CURRENT_SOURCE_DIR}/include
  ${OIS_INCLUDE_DIR}
  ${OGRE_INCLUDE_DIR}
  )

if(APPLE)
  add_executable(${PROJECT_NAME} MACOSX_BUNDLE ${SRCS})
else()
  add_executable(${PROJECT_NAME} ${SRCS})
endif(APPLE)
