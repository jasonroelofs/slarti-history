#ifndef __COMPONENT_MANAGER_H__
#define __COMPONENT_MANAGER_H__

/**
 * Instead of fighting with templates and inheritance to
 * build singleton component containers, just use these two
 * macros in your Manager definition to get all the
 * basic functionality.
 *
 * The Managers that use these macros need to define the following:
 *
 *   // Called after a component is registered
 *   void initialize(ComponentType* component);
 *
 *   // Called before a component is unregistered
 *   void remove(ComponentType* component);
 *
 *   // Called every frame to update all known components
 *   void update(const Ogre::Real& timeSinceLastFrame);
 *
 * Using these macros defines the following on the manager:
 *
 *  // Component added to the system, add it to our collection
 *  // and throw off an initialization request.
 *  void _registerComponent(ComponentType* component);
 *
 *  // Component being removed, clear it from our list
 *  void _unregisterComponent(ComponentType* component);
 *
 *  // Managers are singletons, so use this to get the manager
 *  static ManagerType* getInstance();
 *
 * Because the managers are singletons with initialization you're required to set
 * msInstance to the current manager when you initialize them in the
 * constructor. See TransformManager for an example of usage
 */

#include <vector>
#include <cassert>

#define MANAGER_DEFINITION(ComponentType)                               \
  public:                                                               \
    void _registerComponent( components::ComponentType##Component * component );    \
    void _unregisterComponent( components::ComponentType##Component * component );  \
    static ComponentType##Manager * getInstance();                      \
  protected:                                                            \
    std::vector< components::ComponentType##Component *> mComponents;                            \
    typedef std::vector< components::ComponentType##Component *>::iterator ComponentIterator;    \
    static ComponentType##Manager * msInstance;


#define MANAGER_IMPLEMENTATION(ComponentType) \
  ComponentType##Manager* ComponentType##Manager::msInstance = 0; \
  void ComponentType##Manager::_registerComponent(components::ComponentType##Component * component) { \
    mComponents.push_back(component); \
    initialize(component); \
  } \
  void ComponentType##Manager::_unregisterComponent(components::ComponentType##Component * component) { \
    ComponentIterator it = mComponents.begin(), end = mComponents.end();  \
    for( ; it < end; it++) { \
      if(*it == component) { break; } \
    } \
    remove(component); \
    if(it != end) { mComponents.erase(it); } \
  } \
  ComponentType##Manager* ComponentType##Manager::getInstance() { \
    assert(msInstance && "You have to set msInstance in the Manager's constructor"); \
    return msInstance; \
  }

/**
 * Helpful macro for iterating all components in a manager.
 * The block you give to this macro will contain the variable
 * 'component'
 */
#define EACH_COMPONENT(ComponentType) \
  ComponentType* component; \
  ComponentIterator it = mComponents.begin(); \
  ComponentIterator end = mComponents.end(); \
  for(; it < end; it++) { \
     component = *it;

#endif // __COMPONENT_MANAGER_H__
