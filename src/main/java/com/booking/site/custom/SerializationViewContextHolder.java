package com.booking.site.custom;

import com.booking.site.jsonViews.Views;

public class SerializationViewContextHolder {
   private static ThreadLocal<Class<?>> view=new ThreadLocal<>();
   public static void setView(Class<?> v)
   {
       view.set(v);
   }
   public static Class<?> getView()
   {
       return view.get();
   }
}
