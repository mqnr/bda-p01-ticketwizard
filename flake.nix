{
  description = "Java Maven project with MySQL";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/c46290747b2aaf090f48a478270feb858837bf11";

  outputs = { self, nixpkgs }:
    let
      supportedSystems = [ "x86_64-linux" "aarch64-linux" "x86_64-darwin" "aarch64-darwin" ];
      forEachSupportedSystem = f: nixpkgs.lib.genAttrs supportedSystems (system: f {
        pkgs = import nixpkgs { inherit system; };
      });
      mySqlDataDir = "./sql-data";
    in
    {
      devShells = forEachSupportedSystem ({ pkgs }: {
        default = pkgs.mkShell {
          packages = with pkgs; [
            jdk21
            maven
            mysql80
            jdt-language-server
          ];
        };
      });
    };
}
