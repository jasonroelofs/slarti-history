#ifndef __UTILS_H__
#define __UTILS_H__

#include <cstdlib>

#ifndef max
#define max(a,b) (((a) > (b)) ? (a) : (b))
#define min(a,b) (((a) < (b)) ? (a) : (b))
#endif

namespace Utils {

  /**
   * Pick a random number between two numbers.
   * This uses rand()
   */
  int random(int from, int to) {
    return from + (rand() % (to - from));
  }

}

#endif // __UTILS_H__
