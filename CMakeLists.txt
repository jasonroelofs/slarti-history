
cmake_minimum_required(VERSION 2.6)

project(slartibartfast)

if(APPLE)

  # Set 10.4 as the base SDK by default
  set(XCODE_ATTRIBUTE_SDKROOT macosx10.4)

  # Force arch to be 32-bit always
  set(CMAKE_OSX_ARCHITECTURES "i386")

  # Make sure that the OpenGL render system is selected for non-iPhone Apple builds
  set(OGRE_BUILD_RENDERSYSTEM_GL TRUE)
  set(OGRE_BUILD_RENDERSYSTEM_GLES FALSE)

endif(APPLE)

set(OIS_INCLUDE_DIR ${CMAKE_CURRENT_SOURCE_DIR}/vendor/OIS/include)

subdirs(vendor/OIS)
