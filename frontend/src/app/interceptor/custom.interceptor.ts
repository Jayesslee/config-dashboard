import { HttpInterceptorFn } from '@angular/common/http';

export const customInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');
  const clonedReq = req.clone({
    setHeaders: {
      Authorization: token + '',
    },
  });

  return next(clonedReq);
};
