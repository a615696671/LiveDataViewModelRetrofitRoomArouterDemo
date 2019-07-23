
cmake_minimum_required(VERSION 3.6.0)
set(distribution_DIR ../../../../libs)
#添加lib，SHARED类型，是IMPORTED 引入的库
add_library(libjpeg
        SHARED
        IMPORTED)
#设置 库的属性   里面是名称 ，属性：引入地址把我们的真实地址填写进去
set_target_properties(libjpeg
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/${ANDROID_ABI}/libjpeg.so)

#添加lib，SHARED类型，是IMPORTED 引入的库
add_library(libturbojpeg
        SHARED
        IMPORTED)

#设置 库的属性   里面是名称 ，属性：引入地址把我们的真实地址填写进去
set_target_properties(libturbojpeg
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/${ANDROID_ABI}/libturbojpeg.so)

add_library( # Sets the name of the library.
        jpeg-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        jpeg/jpeg-lib.cpp)


find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)


target_link_libraries( # Specifies the target library.
        jpeg-lib
        libjpeg
        -ljnigraphics
        libturbojpeg
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})






#bassdiffzz
set(bassdiff_DIR ./)
add_library( # Sets the name of the library.
        bassdiff-lib
        # Sets the library as a shared library.
        SHARED
        # Provides a relative path to your source file(s).
       ${bassdiff_DIR}/bassdiff/bassdiffUtils.c)

find_library( # Sets the name of the path variable.
        log-lib
        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)
target_link_libraries( # Specifies the target library.
        bassdiff-lib
        -ljnigraphics
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})