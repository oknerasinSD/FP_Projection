<#macro login path isRegisterForm>
    <form action="${path}" method="post" style="margin-top: 7.5%">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label col-form-label-lg">Логин:</label>
            <div class="col-sm-6">
                <input type="text" name="username" value="<#if user??>${user.username}</#if>"
                       class="form-control form-control-lg ${(usernameError??)?string('is-invalid', '')}"
                       placeholder="Логин" />
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label col-form-label-lg">Пароль:</label>
            <div class="col-sm-6">
                <input type="password" name="password"
                       class="form-control form-control-lg ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="Пароль" />
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label col-form-label-lg">Подтверждение:</label>
                <div class="col-sm-6">
                    <input type="password" name="passwordConfirmation"
                           class="form-control form-control-lg ${(passwordConfirmationError??)?string('is-invalid', '')}"
                           placeholder="Повторите пароль" />
                    <#if passwordConfirmationError??>
                        <div class="invalid-feedback">
                            ${passwordConfirmationError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label col-form-label-lg">Email:</label>
                <div class="col-sm-6">
                    <input type="email" name="email" value="<#if user??>${user.email}</#if>"
                           class="form-control form-control-lg ${(emailError??)?string('is-invalid', '')}"
                           placeholder="Email" />
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <#if !isRegisterForm>
            <a class="btn btn-outline-dark btn-lg"
               href="/registration" role="button"
               style="margin-top: 2%">
                Регистрация
            </a>
        </#if>
        <button class="btn btn-outline-dark btn-lg"
                type="submit" style="margin-top: 2%">
            <#if isRegisterForm>
                Зарегистрироваться
            <#else>
                Войти
            </#if>
        </button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-light"
                style="background-color: #343a40; color: gainsboro; border-color: gainsboro"
                type="submit">
            Выход
        </button>
    </form>
</#macro>