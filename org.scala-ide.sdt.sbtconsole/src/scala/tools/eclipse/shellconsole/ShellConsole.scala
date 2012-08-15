package scala.tools.eclipse.shellconsole

import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.ui.part.IPageBookViewPage
import org.eclipse.ui.console.IConsoleView
import org.eclipse.ui.console.IOConsole
import scala.tools.eclipse.logging.HasLogger
import scala.tools.eclipse.util.SWTUtils
import org.eclipse.swt.widgets.Display
import scala.tools.eclipse.sbtconsole.SWTUtils2

/** 
 * An advanced console based on IOConsole with history support for tab completion. 
 */
class ShellConsole(
  name: String, 
  consoleType: String, 
  imageDescriptor: ImageDescriptor, 
  autoLifecycle: Boolean
) extends IOConsole(name, consoleType, imageDescriptor, autoLifecycle)
    with HasLogger {
  
  protected var page: ShellConsolePage = _
  
  def getPage = page
  
  def this(name: String, imageDescriptor: ImageDescriptor, autoLifecycle: Boolean) { 
    this(name, null, imageDescriptor, autoLifecycle) 
  }

  def this(name: String, imageDescriptor: ImageDescriptor) { 
    this(name, imageDescriptor, true) 
  }

  def this(name: String) { this(name, null) }

  override def createPage(view: IConsoleView): IPageBookViewPage = {
    page = new ShellConsolePage(ShellConsole.this, view)
    page
  }
  
 
  /** Called when the Terminate action is executed. */
  def onTerminate() {
    dispose()
  }
  
  /** Called when the Restart action is executed. */
  def onRestart() {}
  
  /** Notifies this console that the specified text has been appended to it. */
  protected[shellconsole] def textAppended(text: String) {
    if (page != null) {
      page.getListener.moveCaretToEndAsync() 
    }
  }
}
