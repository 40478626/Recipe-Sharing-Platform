import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'login-cmp',
    moduleId: module.id,
    templateUrl: 'login.component.html'
})
export class LoginComponent {
    username: string = '';
    password: string = '';

    constructor(private http: HttpClient, private router: Router) {}

    login() {
        const tokenUrl = 'http://localhost:8080/realms/recipe-sharing-platform/protocol/openid-connect/token';

        const body = new HttpParams()
            .set('client_id', 'recipe-app-client')
            .set('grant_type', 'password')
            .set('client_secret', 'PzQBoAj8vUNCDvj9Qp1IAqu0mhkZFsIP')
            .set('username', this.username)
            .set('password', this.password);

        const headers = new HttpHeaders({
            'Content-Type': 'application/x-www-form-urlencoded'
        });

        this.http.post(tokenUrl, body.toString(), { headers })
            .subscribe(
                (response: any) => {
                    console.log("Keycloak Token Response:", response);

                    const accessToken = response.access_token;
                    if (accessToken) {
                        sessionStorage.setItem('authToken', accessToken);
                        console.log("Stored Access Token:", accessToken);

                        this.router.navigate(['/dashboard']);
                    } else {
                        alert("Login failed: No access token received.");
                    }
                },
                (error) => {
                    console.error('Login error:', error);
                    alert('Login failed. Please check your credentials.');
                }
            );
    }
}
