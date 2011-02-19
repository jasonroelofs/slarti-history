
cmake_minimum_required(VERSION 2.6)

set(PROJECT_NAME slartibartfast)
project(${PROJECT_NAME})

set(OGRE_HOME $ENV{OGRE_HOME})

set(CMAKE_MODULE_PATH ${OGRE_HOME}/CMake)

if(APPLE)
  set(XCODE_ATTRIBUTE_SDKROOT macosx10.4)
  set(CMAKE_OSX_ARCHITECTURES "i386")
  SET(EXETYPE MACOSX_BUNDLE)

  FIND_LIBRARY(COCOA_LIBRARY Cocoa)
  FIND_LIBRARY(CARBON_LIBRARY Carbon)
  FIND_LIBRARY(IOKit_LIBRARY IOKit)
  MARK_AS_ADVANCED(COCOA_LIBRARY)
  MARK_AS_ADVANCED(CARBON_LIBRARY)
  MARK_AS_ADVANCED(IOKit_LIBRARY)

  set(EXTRA_LIBS ${COCOA_LIBRARY} ${CARBON_LIBRARY} ${IOKit_LIBRARY})
endif(APPLE)

FILE(GLOB_RECURSE DATA "bin/*")
FILE(GLOB_RECURSE SRCS "src/*.cpp")
FILE(GLOB_RECURSE HDRS "include/*.h")

SET(SLARTI_INCLUDES
  ${CMAKE_CURRENT_SOURCE_DIR}/include
  ${CMAKE_CURRENT_SOURCE_DIR}/include/generators
  ${CMAKE_CURRENT_SOURCE_DIR}/include/generators/voxel
)


include_directories(
  ${OGRE_HOME}/boost_1_42
  ${OGRE_HOME}/include
  ${OGRE_HOME}/include/OGRE
  ${OGRE_HOME}/include/OGRE/OSX
  ${OGRE_HOME}/include/OIS
  ${SLARTI_INCLUDES}
)

link_directories(
  ${OGRE_HOME}/boost_1_42/lib
  ${OGRE_HOME}/lib
  ${OGRE_HOME}/lib/release
)

if(APPLE)
  add_executable(${PROJECT_NAME} MACOSX_BUNDLE ${SRCS} ${DATA})

  ##
  # Install the files directly into the app bundle.
  # Do this for building a deployable release
  ##
  SET_SOURCE_FILES_PROPERTIES(${DATA} PROPERTIES MACOSX_PACKAGE_LOCATION Resources )

  ## 
  # Symlink the data files into the bundle so we don't need
  # to rebuild for changes to them
  ##
  #add_custom_command(TARGET ${PROJECT_NAME} POST_BUILD
  #  COMMAND mkdir ARGS -p ${MACOSX_PACKAGE_LOCATION}/Resources
  #	COMMAND ln ARGS -s -f bin/plugins.cfg 
  #	${OGRE_SAMPLE_CONTENTS_PATH}/Resources/
  #	COMMAND ln ARGS -s -f ${OGRE_BINARY_DIR}/bin/resources.cfg 
  #	${OGRE_SAMPLE_CONTENTS_PATH}/Resources/
  #	)

  set_target_properties(${PROJECT_NAME} PROPERTIES
    LINK_FLAGS "-F${OGRE_HOME}/lib/release -framework Ogre"
  )
else()
  add_executable(${PROJECT_NAME} ${SRCS})
endif(APPLE)

target_link_libraries(${PROJECT_NAME} OIS ${EXTRA_LIBS})


