# DUPLICATE FILE FINDER

## TODO

- Stream IO MD5
- Output summary
- Compare file size also
- 
## Usage

Finds duplicate files in a folder. Returns information in JSON format.

`java -jar duplicate-file-finder.jar` (Runs Duplicate File Finder in the current directory)

`java -jar duplicate-file-finder.jar -d photos` (Runs Duplicate File Finder in the given directory)

`java -jar duplicate-file-finder.jar resultProcessor dff.json` (Runs the result processor on file dff.json)


### Options 

`<options>` 

`-o|--output-file` File to output to. 

`-min|--minFiles`  (default 2) Only return results greater or equal to this minimum

`-i|--include`     Filter files also by extension (multiple includes allowed)

`-r|--regex`       Filter files also by regex

`-q|--quick`       Just MD5 the first 1000 bytes

## Output

```
{
  "hashes": [
    {
      "md5": "20838a8df7cc0babd745c7af4b7d94e2",
      "fileSize": 20,
      "files": [
        "./file1.txt"
      ]
    },
    {
      "md5": "8b75a401511bfe72ff871b13845befb5",
      "fileSize": 1000,
      "files": [
        "./file2.txt",
        "./file3.txt"
      ]
    }
  ]
}
```
