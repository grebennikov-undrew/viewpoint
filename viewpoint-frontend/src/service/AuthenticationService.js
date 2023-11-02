import { httpRequest } from "./httpRequest";

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'username'
export let globalToken;

class AuthenticationService {

    executeBasicAuthenticationService(username, password) {
        return httpRequest.get(`/auth/basic_auth`,
            { headers: { authorization: this.createBasicAuthToken(username, password) }})
            // { headers: { authorization: this.createBasicAuthToken(username, password) }})
    }

    executeJwtAuthenticationService(username, password) {
        console.log(username);
        return httpRequest.post(`/authenticate`, {
            username,
            password
        })
    }

    createBasicAuthToken(username, password) {
        const token = window.btoa(username + ":" + password);
        return 'Basic ' + token
    }

    registerSuccessfulLogin(username, password) {
        //let basicAuthHeader = 'Basic ' +  window.btoa(username + ":" + password)
        //console.log('registerSuccessfulLogin')
        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username)
        this.setupAxiosInterceptors(this.createBasicAuthToken(username, password))
    }

    registerSuccessfulLoginForJwt(username, token) {
        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username)
        this.setupAxiosInterceptors(this.createJWTToken(token))
    }

    createJWTToken(token) {
        return 'Bearer ' + token
    }


    logout() {
        sessionStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        if (user === null) return false
        return true
    }

    getLoggedInUserName() {
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        if (user === null) return ''
        return user
    }

    setupAxiosInterceptors(token) {
        globalToken = token;
    }

    getGlobalToken() {
        return globalToken;
    }
}

export default new AuthenticationService()