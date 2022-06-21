using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using SkuciSeAPI.Models;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;

namespace SkuciSeAPI.Common
{
    public class JWTTokenHelper
    {
        private static readonly JWTTokenHelper instance;
        private static string _secret = "c0bfd2b52f9c17663fc7fec69aac35f4d2894ca4";
        private static string _issuer = "Tim2";

        static JWTTokenHelper()
        {
            instance = new JWTTokenHelper();
        }

        public static JWTTokenHelper Jwt
        {
            get
            {
                return instance;
            }
        }

        public UserLogin GenerateJSONWebToken(UserLogin userInfo, byte userRole, long userId)
        {
            var securityKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_secret));
            var credentials = new SigningCredentials(securityKey, SecurityAlgorithms.HmacSha256);

            var token = new JwtSecurityToken(issuer: _issuer,
               audience: _issuer,
               claims: new[] {
                    new Claim(JwtRegisteredClaimNames.Email,userInfo.Email),
                    new Claim(ClaimTypes.Role,((Constants.UserRole)userRole).ToString()),
                    new Claim(JwtRegisteredClaimNames.Jti,Guid.NewGuid().ToString())
               },
               expires: DateTime.Now.AddHours(7),
               signingCredentials: credentials);

            userInfo.Id = userId;
            userInfo.Role = userRole;
            userInfo.Password = "SECRET";
            userInfo.Token = new JwtSecurityTokenHandler().WriteToken(token);

            return userInfo;
        }

        public string? ValidateJSONWebToken(UserLogin userInfo)
        {
            var tokenHandler = new JwtSecurityTokenHandler();
            var securityKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_secret));

            var validationParameters = new TokenValidationParameters
            {
                ValidateIssuerSigningKey = true,
                IssuerSigningKey = securityKey,

                ValidateIssuer = true,
                ValidateAudience = true,

                ValidIssuer = _issuer,
                ValidAudience = _issuer
            };

            SecurityToken validatedToken;
            try
            {
                tokenHandler.ValidateToken(userInfo.Token, validationParameters, out validatedToken);
            }
            catch (Exception)
            {
                throw new Exception("Error while validating token.");
            }

            var jwtToken = (JwtSecurityToken)validatedToken;
            var userEmail = jwtToken.Claims.First(x => x.Type == "email").Value.ToString();

            return userEmail;
        }
    }
}
