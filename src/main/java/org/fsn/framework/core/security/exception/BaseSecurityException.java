package org.fsn.framework.core.security.exception;


import org.fsn.framework.common.exception.error.BaseBusinessModuleException;
import org.fsn.framework.common.exception.error.DefaultError;

/**
 * Created by sorta on 5/8/16.
 */
public class BaseSecurityException extends BaseBusinessModuleException
{
   private static final long serialVersionUID = 5962448653978019661L;

   public BaseSecurityException()
   {
      super();
      error = DefaultError.INVALID_TOKEN;
   }

   public BaseSecurityException(String s)
   {
      super(s);
      this.error = DefaultError.INVALID_TOKEN;
   }

   public BaseSecurityException(String s, Throwable throwable)
   {
      super(s, throwable);
      this.error = DefaultError.INVALID_TOKEN;
   }

   public BaseSecurityException(Throwable throwable)
   {
      super(throwable);
      this.error = DefaultError.INVALID_TOKEN;
   }
}
