package com.studio.jframework.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.os.Looper;
import android.text.format.Formatter;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

public class FreeMemory
{
  private static FreeMemory c;
  private Context a;
  private ActivityManager b = null;
  private long d = 0L;
  private long e = 0L;

  public FreeMemory(Context paramContext)
  {
    this.a = paramContext;
//    this.b = ((ActivityManager)this.a.getSystemService("activity"));
  }

  public static FreeMemory getInstance(Context paramContext)
  {
    if (c == null)
      c = new FreeMemory(paramContext);
    return c;
  }

  public List<RunningTaskInfo> _getRunningTask()
  {
    List localList = this.b.getRunningTasks(100);
    MemoryInfo localMemoryInfo = new MemoryInfo();
    this.b.getMemoryInfo(localMemoryInfo);
    return localList;
  }

  public void freeMemory()
  {
//    new Thread(new su(this)).start();
  }

  public void getProcessList()
  {
    freeMemory();
  }

  public void getRunningProcess()
  {
    List localList = this.b.getRunningAppProcesses();
    this.d = getRunningTaskMemory().longValue();
    LogUtils.e("weijiang.Zeng", "可用内存: " + Formatter.formatFileSize(this.a, this.d));
    Iterator localIterator = localList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        this.e = getRunningTaskMemory().longValue();
        Looper.prepare();
        if (this.e > this.d)
//          new AlertDialog.Builder(this.a).setMessage("释放内存: " + Formatter.formatFileSize(this.a, this.e - this.d)).setPositiveButton("OK", null);
        Log.e("weijiang.Zeng", "可用内存: " + Formatter.formatFileSize(this.a, this.e));
        Log.e("weijiang.Zeng", "释放内存: " + Formatter.formatFileSize(this.a, this.e - this.d));
        Looper.loop();
        return;
      }
      RunningAppProcessInfo localRunningAppProcessInfo = (RunningAppProcessInfo)localIterator.next();
      if ((localRunningAppProcessInfo.processName.equals("system")) || (localRunningAppProcessInfo.processName.equals("com.android.phone")))
        continue;
      if (localRunningAppProcessInfo.processName.equals("com.jecainfo.weican"))
      {
        Log.e("weijiang.Zeng", "包师傅  项目进程,不释放...");
        continue;
      }
      this.b.restartPackage(localRunningAppProcessInfo.processName);
      this.b.killBackgroundProcesses(localRunningAppProcessInfo.processName);
    }
  }

  public Long getRunningTaskMemory()
  {
    MemoryInfo localMemoryInfo = new MemoryInfo();
    this.b.getMemoryInfo(localMemoryInfo);
    return Long.valueOf(localMemoryInfo.availMem);
  }
}

/* Location:           C:\Users\Administrator\Desktop\com.jecainfo.weican_2.10_2100_dex2jar.jar
 * Qualified Name:     com.bsf.cook.util.FreeMemory
 * JD-Core Version:    0.6.0
 */