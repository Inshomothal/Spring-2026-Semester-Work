param(
	[string]$ClassName = "DiceSimulator"
)

$javafxLib = "$env:USERPROFILE\javafx\javafx-sdk-24.0.1\lib"
$sourceFile = ".\$ClassName.java"

if (-not (Test-Path $sourceFile)) {
	Write-Error "Source file not found: $sourceFile"
	exit 1
}

# If running inside a package folder (like src\lab11), compile to parent (src).
$classOutputRoot = Resolve-Path ".."

$packageLine = Select-String -Path $sourceFile -Pattern '^\s*package\s+([\w\.]+)\s*;' | Select-Object -First 1
$packageName = $null
if ($packageLine) {
	$packageName = $packageLine.Matches[0].Groups[1].Value
}

$mainClass = if ($packageName) { "$packageName.$ClassName" } else { $ClassName }

javac --module-path "$javafxLib" --add-modules javafx.controls,javafx.fxml -d "$classOutputRoot" $sourceFile
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

java --module-path "$javafxLib" --add-modules javafx.controls,javafx.fxml -cp "$classOutputRoot" $mainClass