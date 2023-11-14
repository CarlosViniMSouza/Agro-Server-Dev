package com.qrmenu.qrmenuserver.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.qrmenu.qrmenuserver.restaurants.IRestaurantRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterProductAuth extends OncePerRequestFilter {

    @Autowired
    private IRestaurantRepository restaurantRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath();
        if (servletPath.startsWith("/products/")) {
            var authorization = request.getHeader("Authorization");
            var authEncoded = authorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            var authString = new String(authDecode);

            String[] credentials = authString.split(":");
            String name = credentials[0];
            String password = credentials[1];

            System.out.println("Authorization");
            System.out.println(name);
            System.out.println(password);

            var restaurant = this.restaurantRepository.findByName(name);

            if (restaurant == null) {
                response.sendError(401, "Restaurant without Authorization");
            } else {
                // Validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), restaurant.getPassword());
                if (passwordVerify.verified) {
                    // Segue viagem
                    request.setAttribute("idRestaurant", restaurant.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
