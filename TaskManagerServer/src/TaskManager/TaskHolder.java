package TaskManager;

/**
* TaskManager/TaskHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from TaskManager.idl
* Friday, January 31, 2025 10:23:31 PM WET
*/

public final class TaskHolder implements org.omg.CORBA.portable.Streamable
{
  public TaskManager.Task value = null;

  public TaskHolder ()
  {
  }

  public TaskHolder (TaskManager.Task initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = TaskManager.TaskHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    TaskManager.TaskHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return TaskManager.TaskHelper.type ();
  }

}
