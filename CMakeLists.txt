
cmake_minimum_required(VERSION 2.6)

project(slartibartfast)

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

subdirs(vendor/Ogre)
