#include <Application.h>
#include "Game.h"

int main(int argc, char *argv[])
{
	QtOgre::Application app(argc, argv, new Game());
	return app.exec();
}
