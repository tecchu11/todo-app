# Libs for bearer-auth

Provide bearer auth service.
You can generate, verify bearer token based jwt via BearerTokenService.

## AutoConfiguration

You have to configure below properties.

| property                     | detail                        |
|------------------------------|-------------------------------|
| libs.bearer-token.issuer     | token issuer                  |
| libs.bearer-token.expire-at  | expired sec for token         |
| libs.bearer-token.secret-key | secret key for token(HMAC256) |


### Example

```yaml
libs:
  bearer-token:
    jwt:
      issuer: my-application-name
      expire-at: PT24H # See ISO_8601. This means 24hours. Ofcourse, you can write seconds
      secret-key: ownKey
```
