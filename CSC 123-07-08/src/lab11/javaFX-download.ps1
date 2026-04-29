$Version = "24.0.1"
$Arch = "x64"
$ZipName = "openjfx-${Version}_windows-${Arch}_bin-sdk.zip"
$Url = "https://download2.gluonhq.com/openjfx/$Version/$ZipName"
$DestRoot = "$env:USERPROFILE\javafx"
$ZipPath = "$DestRoot\$ZipName"

New-Item -ItemType Directory -Path $DestRoot -Force | Out-Null
Invoke-WebRequest -Uri $Url -OutFile $ZipPath -Verbose
Expand-Archive -Path $ZipPath -DestinationPath $DestRoot -Force

Write-Host "JavaFX extracted to: $DestRoot"