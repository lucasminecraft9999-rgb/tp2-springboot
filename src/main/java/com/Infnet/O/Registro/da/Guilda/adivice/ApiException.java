    package com.Infnet.O.Registro.da.Guilda.adivice;

    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;

    import java.util.List;
    import java.util.Map;

    @RestControllerAdvice
    public class ApiException {
    @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<?> handleException(RuntimeException e) {

            erroApi erro = new erroApi(
                    "Solicitação invalida" ,
                    List.of(e.getMessage())
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
    }
