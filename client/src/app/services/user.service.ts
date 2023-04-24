import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";

@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }

  postRegister(data: any): Promise<any> {
    const headers = new HttpHeaders()
        .set('Content-Type','application/json')
        .set('Accept','application/json');

    return firstValueFrom<any>(
      this.http.post<any>('https://bewildered-stitch-production.up.railway.app/api/v1/auth/register', data, { headers })
    );
  }

  getVerified(data: any): Promise<any>{
    let params = new HttpParams()
        .set('verificationString', data);
    return firstValueFrom<any>(
      this.http.get<any>('https://bewildered-stitch-production.up.railway.app/api/v1/auth/verify', { params })
    )
  }

  postForgotPassword(data: any): void{
    const headers = new HttpHeaders()
        .set('Content-Type','application/json')
        .set('Accept','application/json');

    this.http.post<void>('https://bewildered-stitch-production.up.railway.app/api/v1/auth/forgotPassword', data, { headers }).subscribe();
  }

  putResetPassword(data: any): Promise<any>{
    const headers = new HttpHeaders()
        .set('Content-Type','application/json')
        .set('Accept','application/json');

    let params = new HttpParams()
        .set('resetPwdString', data);
    return firstValueFrom<any>(
      this.http.put<any>('https://bewildered-stitch-production.up.railway.app/api/v1/auth/resetPassword', {}, { params, headers })
    )
  }

  postLogin(data: any): Promise<any>{
    const headers = new HttpHeaders()
        .set('Content-Type','application/json')
        .set('Accept','application/json');
    return firstValueFrom<any>(
      this.http.post<any>('https://bewildered-stitch-production.up.railway.app/api/v1/auth/authenticate', data, { headers })
    )
  }

  getUser(token: any): Promise<any>{
    const headers = new HttpHeaders()
        .set('Content-Type','application/json')
        .set('Accept','application/json')
        .set('Authorization', `Bearer ${token}`);
    return firstValueFrom<any>(
      this.http.get<any>('https://bewildered-stitch-production.up.railway.app/api/v1/user/getUser', { headers })
    )
  }

  putChangePassword(data: any, token: any): Promise<any>{
    const headers = new HttpHeaders()
        .set('Content-Type','application/json')
        .set('Accept','application/json')
        .set('Authorization', `Bearer ${token}`);
    return firstValueFrom<any>(
      this.http.put('https://bewildered-stitch-production.up.railway.app/api/v1/auth/changePassword', data, { headers })
    )
  }
}