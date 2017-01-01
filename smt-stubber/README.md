<!---
Copyright 2015 Karl Bennett

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->
smt-stubber
===========

This library provides a simple way of stubbing HTTP endpoints.

### Stubbing

Endpoints are stubbed by following a simple directory and file convention.

##### Directory convention

To stub an endpoint for an HTTP method and path simply create a directory structure that starts with the HTTP method and 
matches the path. So to setup a stub that will match the following `curl` command.
```bash
curl -XGET http://localhost:8080/one/two/three
```

You would need the following directory structure.
```bash
mkdir -p GET/one/two/three
tree GET
GET
└── one
    └── two
        └── three
```

If you wish to provide some default stubbing for any other paths under `/one/two/` that don't have an explicit directory
then you can place those responses under a directory named `_`.
```bash
mkdir -p GET/one/two/_
tree GET
GET
└── one
    └── two
        └── _
```

This stubbing would then match the following requests as long as no explicit directories are setup for them.
```bash
curl -XGET http://localhost:8080/one/two/four
curl -XGET http://localhost:8080/one/two/five
curl -XGET http://localhost:8080/one/two/six
```

##### File convention

Within the final directory of any stubbed path you need to supply some files that will tell the stub how to respond. 

**`RequestHeaders-{name}.json`**
```json
{
  "Accept": "application/json",
  "Custom-Header": ["one", "two", "three"]
}
```
The headers file should contain all the headers that are expected to be contained within a required request. The 
`{name}` is any name you wish to give this request, this name is used to match the headers to a body and response. Any 
incoming request will match the file as long as it contains all it's headers, any extra headers within the request will 
be ignored.

**`RequestBody-{name}.[json|xml|form|txt|...]`**

**`RequestQuery-{name}.form]`**

These files should contain the expected body or query string of the request. The `RequestQuery` file must contain form 
URL encoded text, where as the `RequestBody` files can contain anything. The file contents will be matched, ignoring 
whitespace and casing, to the body of the request that matched the related `RequestHeaders` file. JSON, XML, and 
form URL encoded content will be parsed and matched by value so the contents must contain the exact same values, but the 
values can be in any order.

If the request is not meant to contain a body or query string then these files can be omitted.

**`ResponseHeaders-{name}.json`**
```json
{
  "status": 200,
  "Content-Type": "application/json",
  "Custom-Header": ["four", "five", "six"]
}
```
If an incoming request matches all the request files of the given `{name}` then this file will be used to set the status
and headers that will be sent back in the response.

**`ResponseBody-{name}.[json|xml|txt|...]`**

Just like the `ResponseHeaders` file above the contents of this file will be used as the body of the response. 

If the response should not contain a body then this file can be omitted.

**`ResponseHeaders.json`**

**`ResponseBody.[json|xml|txt|...]`**

Response files with no `{name}` will be used for a default response. If no default response files are supplied then the
stub will respond with a `404` with no body.