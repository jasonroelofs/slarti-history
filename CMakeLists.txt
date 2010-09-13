
cmake_minimum_required(VERSION 2.6)

set(PROJECT_NAME slartibartfast)
project(${PROJECT_NAME})

set(CMAKE_MODULE_PATH ${CMAKE_CURRENT_SOURCE_DIR}/ext/CMake)

if(APPLE)
  set(XCODE_ATTRIBUTE_SDKROOT macosx10.4)
  set(CMAKE_OSX_ARCHITECTURES "i386")

  SET(EXETYPE MACOSX_BUNDLE)
endif(APPLE)

find_package(Boost QUIET)

# Set up referencing of Boost
include_directories(${Boost_INCLUDE_DIR})
add_definitions(-DBOOST_ALL_NO_LIB)

FILE(GLOB_RECURSE DATA "bin/*")
FILE(GLOB_RECURSE SRCS "src/*.cpp")
FILE(GLOB_RECURSE HDRS "include/*.h")

include_directories(
  ${CMAKE_CURRENT_SOURCE_DIR}/include
  ${CMAKE_CURRENT_SOURCE_DIR}/ext/include
  ${CMAKE_CURRENT_SOURCE_DIR}/ext/include/OGRE
  ${CMAKE_CURRENT_SOURCE_DIR}/ext/include/OGRE/OSX
  ${CMAKE_CURRENT_SOURCE_DIR}/ext/include/OIS
)

link_directories(
  ${CMAKE_CURRENT_SOURCE_DIR}/ext/lib
  ${CMAKE_CURRENT_SOURCE_DIR}/ext/lib/debug
)

if(APPLE)
  add_executable(${PROJECT_NAME} MACOSX_BUNDLE ${SRCS} ${DATA})

 	SET_SOURCE_FILES_PROPERTIES(${DATA} PROPERTIES MACOSX_PACKAGE_LOCATION Resources )

  target_link_libraries(${PROJECT_NAME} OIS)
  set_target_properties(${PROJECT_NAME} PROPERTIES
    LINK_FLAGS "-F${CMAKE_CURRENT_SOURCE_DIR}/ext/lib/debug/Debug -framework Ogre"
  )
else()
  add_executable(${PROJECT_NAME} ${SRCS})
endif(APPLE)


